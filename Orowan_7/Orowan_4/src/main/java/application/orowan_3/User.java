package application.orowan_3;

public class User {
    private String ingPwd;
    private String workerPwd;

    public User(String pwd_worker, String ing_pwd) {
        workerPwd = pwd_worker;
        ingPwd = ing_pwd;
    }

    public void updateWorkerPwd(String pwd) {
        workerPwd = pwd;
    }

    public void updateIngPwd(String pwd) {
        ingPwd = pwd;
    }

    public String getIngPwd() {
        return ingPwd;
    }

    public String getWorkerPwd() {
        return workerPwd;
    }
}