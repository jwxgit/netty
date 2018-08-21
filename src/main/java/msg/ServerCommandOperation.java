package msg;

import java.io.Serializable;

public class ServerCommandOperation implements Serializable{
    private  String chilCode;
    private  String  operation;
    private  String isCancel;
    public String getChilCode() {
        return chilCode;
    }

    public void setChilCode(String chilCode) {
        this.chilCode = chilCode;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public String toString() {
        return "ServerCommandOperation{" +
                "chilCode='" + chilCode + '\'' +
                ", operation='" + operation + '\'' +
                ", isCancel='" + isCancel + '\'' +
                '}';
    }
}
