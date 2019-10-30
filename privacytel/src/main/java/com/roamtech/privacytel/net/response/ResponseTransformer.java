package com.roamtech.privacytel.net.response;

import com.roamtech.privacytel.net.exception.ApiException;
import com.roamtech.privacytel.net.exception.NormalException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {
    private static <T> T getNewObject(Class<T> cls) {
        T t=null;
        try {
            t = cls.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> ObservableTransformer<Response<T>, T> handleResult() {
        return handleResult(null);
    }

    public static <T> ObservableTransformer<Response<T>, T> handleResult(Class<T> cls) {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<T>())
                .flatMap(new ResponseFunction<T>(cls));
    }

    /**
     * 非服务器产生的异常，比如本地无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends Response<T>>> {
        @Override
        public ObservableSource<? extends Response<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(NormalException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<Response<T>, ObservableSource<T>> {
        private Class<T> cls;
        public ResponseFunction(Class<T> cls){
            this.cls = cls;
        }
        @Override
        public ObservableSource<T> apply(Response<T> tResponse) throws Exception {
            int code = tResponse.getStatus();
            String message = tResponse.getMessage();
            if (code == 200) {
                if(null != tResponse.getData()){
                    return Observable.just(tResponse.getData());
                } else if(null != cls){
                    return Observable.just(getNewObject(cls));
                }
            }
            return Observable.error(new ApiException(code, message));
        }
    }
}
