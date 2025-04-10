package dmit2015.faces;

import dmit2015.ejb.ProgrammaticTimersManagerBean;
import jakarta.ejb.Timer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class TimerView {

    @Inject
    private ProgrammaticTimersManagerBean timerBean;

    public String cancelAllTimers() {
        timerBean.cancelAllTimers();;
        return "";
    }

        public void cancelTimer(Timer selectedTimer) {
        timerBean.cancelTimer(selectedTimer);
    }

    public Collection<Timer> list() {
        return timerBean.listAllTimers();
    }
}