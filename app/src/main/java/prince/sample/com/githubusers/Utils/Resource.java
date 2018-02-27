package prince.sample.com.githubusers.Utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static prince.sample.com.githubusers.Utils.Status.ERROR;
import static prince.sample.com.githubusers.Utils.Status.LOADING;
import static prince.sample.com.githubusers.Utils.Status.SUCCESS;

public class Resource<T> {

    public final Status status;
    public final String message;
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}