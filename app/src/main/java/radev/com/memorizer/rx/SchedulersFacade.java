package radev.com.memorizer.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created here and now by radek.
 * Peace and love.
 */

public class SchedulersFacade {
    /**
     * IO thread pool scheduler
     */
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Computation thread pool scheduler
     */
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Main Thread scheduler
     */
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
