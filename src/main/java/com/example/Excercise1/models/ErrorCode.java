package com.example.Excercise1.models;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Category;

public class ErrorCode implements Serializable {
    private static final long serialVersionUID = 1L;
    public final Map<Integer, List<String>> errorMsg = new LinkedHashMap();
    private boolean disableSubmit = false;
    public static final int LOG_DEBUG = 0;
    public static final int LOG_INFO = 1;
    public static final int LOG_WARN = 2;
    public static final int LOG_ERROR = 3;
    private String command = null;

    public ErrorCode() {
    }

    public void addErrorMsg(int code, String msg) {
        if (this.errorMsg.containsKey(code)) {
            List<String> values = (List)this.errorMsg.get(code);
            values.add(msg);
            this.errorMsg.put(code, values);
        } else {
            List<String> values = new ArrayList();
            values.add(msg);
            this.errorMsg.put(code, values);
        }

    }

    public void addErrorMsgIfUnique(int code, String msg) {
        if (!this.containsErrorMsg(code)) {
            this.addErrorMsg(code, msg);
        }

    }

    public void addErrorMsgIfUniqueMsg(int code, String msg) {
        boolean found = false;
        Enumeration<String> msgs = this.getErrorMsgMultiple(code);

        while(msgs.hasMoreElements()) {
            if (msg.equals(msgs.nextElement())) {
                found = true;
                break;
            }
        }

        if (!found) {
            this.addErrorMsg(code, msg);
        }

    }

    public synchronized void addPrefix(String prefix) {
        Enumeration<Integer> en = this.getErrorMsgCodeEnum();
        List<AbstractMap.SimpleEntry<Integer, String>> msgList = new ArrayList();
        boolean addedPrefix = false;

        while(en.hasMoreElements()) {
            Integer key = (Integer)en.nextElement();

            String msg;
            for(Enumeration<String> msgs = this.getErrorMsgMultiple(key); msgs.hasMoreElements(); msgList.add(new AbstractMap.SimpleEntry(key, msg))) {
                msg = (String)msgs.nextElement();
                if (!msg.startsWith(prefix)) {
                    msg = prefix + ": " + msg;
                    addedPrefix = true;
                }
            }
        }

        if (addedPrefix) {
            this.deleteMsgs();
            Iterator var9 = msgList.iterator();

            while(var9.hasNext()) {
                AbstractMap.SimpleEntry<Integer, String> pair = (AbstractMap.SimpleEntry)var9.next();
                this.addErrorMsg((Integer)pair.getKey(), (String)pair.getValue());
            }
        }

    }

    public void deleteMsgs() {
        this.errorMsg.clear();
    }

    public String getErrorMsgByCode(int msgNum) {
        String msg = null;
        if (this.errorMsg != null && this.errorMsg.get(new Integer(msgNum)) != null) {
            List<String> msgList = (List)this.errorMsg.get(msgNum);
            if (msgList != null && !msgList.isEmpty()) {
                msg = (String)msgList.get(msgList.size() - 1);
            }
        }

        return msg;
    }

    public String getErrorMsg(int index) {
        List<AbstractMap.SimpleEntry<Integer, String>> errorMsgList = this.getErrorMsgListFromMap();
        AbstractMap.SimpleEntry<Integer, String> pair = (AbstractMap.SimpleEntry)errorMsgList.get(index);
        return (String)pair.getValue();
    }

    private List<AbstractMap.SimpleEntry<Integer, String>> getErrorMsgListFromMap() {
        List<AbstractMap.SimpleEntry<Integer, String>> errorMsgList = new ArrayList();
        new ArrayList();
        Set<Integer> keys = this.errorMsg.keySet();
        Iterator var5 = keys.iterator();

        while(var5.hasNext()) {
            Integer key = (Integer)var5.next();
            List<String> messages = (List)this.errorMsg.get(key);
            Iterator var7 = messages.iterator();

            while(var7.hasNext()) {
                String msg = (String)var7.next();
                errorMsgList.add(new AbstractMap.SimpleEntry(key, msg));
            }
        }

        return errorMsgList;
    }

    public Enumeration<String> getErrorMsgMultiple(int msgNum) {
        return Collections.enumeration((Collection)this.errorMsg.get(msgNum));
    }

    public boolean containsErrorMsg(int msgNum) {
        return this.errorMsg.get(new Integer(msgNum)) != null;
    }

    /** @deprecated */
    public List<String> getErrorMsgs() {
        List<String> values = new ArrayList();
        Iterator var3 = this.errorMsg.values().iterator();

        while(var3.hasNext()) {
            List<String> valueList = (List)var3.next();
            Iterator var5 = valueList.iterator();

            while(var5.hasNext()) {
                String value = (String)var5.next();
                values.add(value);
            }
        }

        return values;
    }

    public Enumeration<String> getErrorMsgEnum() {
        List<String> values = new ArrayList();
        Iterator var3 = this.errorMsg.values().iterator();

        while(var3.hasNext()) {
            List<String> valueList = (List)var3.next();
            Iterator var5 = valueList.iterator();

            while(var5.hasNext()) {
                String value = (String)var5.next();
                values.add(value);
            }
        }

        return Collections.enumeration(values);
    }

    public int getErrorMsgCode(int index) {
        List<AbstractMap.SimpleEntry<Integer, String>> errorMsgList = this.getErrorMsgListFromMap();
        AbstractMap.SimpleEntry<Integer, String> pair = (AbstractMap.SimpleEntry)errorMsgList.get(index);
        return (Integer)pair.getKey();
    }

    /** @deprecated */
    public List<Integer> getErrorMsgCodes() {
        Enumeration<Integer> en = this.getErrorMsgCodeEnum();
        List<Integer> messageCodes = new ArrayList();

        while(en.hasMoreElements()) {
            messageCodes.add((Integer)en.nextElement());
        }

        return messageCodes;
    }

    public Enumeration<Integer> getErrorMsgCodeEnum() {
        Enumeration<Integer> en = Collections.enumeration(this.errorMsg.keySet());
        return en;
    }

    public int getNumErrors() {
        List<AbstractMap.SimpleEntry<Integer, String>> errorMsgList = this.getErrorMsgListFromMap();
        return errorMsgList.size();
    }

    public void merge(ErrorCode src) {
        if (src != null) {
            for(int i = 0; i < src.getNumErrors(); ++i) {
                int num = src.getErrorMsgCode(i);
                String msg = src.getErrorMsg(i);
                this.addErrorMsg(num, msg);
            }

        }
    }

    public void mergeUnique(ErrorCode src) {
        if (src != null) {
            for(int i = 0; i < src.getNumErrors(); ++i) {
                int num = src.getErrorMsgCode(i);
                String msg = src.getErrorMsg(i);
                this.addErrorMsgIfUnique(num, msg);
            }

        }
    }

    public void setDisableSubmit(boolean disable) {
        this.disableSubmit = disable;
    }

    public boolean getDisableSubmit() {
        return this.disableSubmit;
    }

    public String toString() {
        return this.errorMsg.toString();
    }

    public void logMessages(Category log, int type) {
        Enumeration<String> en = this.getErrorMsgEnum();

        while(en.hasMoreElements()) {
            this.log(log, (String)en.nextElement(), type);
        }

    }

    public List<String> getErrorMessageList() {
        List<String> errMsgList = new ArrayList();
        Enumeration<Integer> errorEnum = this.getErrorMsgCodeEnum();
        String msg = null;
        Enumeration<String> msgEnum = null;
        int err = false;
        Integer errCd = null;

        while(errorEnum.hasMoreElements()) {
            errCd = (Integer)errorEnum.nextElement();
            int err = errCd;
            msgEnum = this.getErrorMsgMultiple(err);

            while(msgEnum.hasMoreElements()) {
                msg = (String)msgEnum.nextElement();
                errMsgList.add(err + " - " + msg);
            }
        }

        return errMsgList;
    }

    public List<String> getErrorMessageListWithoutErrCd() {
        List<String> errorMessageList = new ArrayList();
        Enumeration<Integer> errorEnum = this.getErrorMsgCodeEnum();

        while(errorEnum.hasMoreElements()) {
            Enumeration<String> msgEnum = this.getErrorMsgMultiple((Integer)errorEnum.nextElement());

            while(msgEnum.hasMoreElements()) {
                errorMessageList.add((String)msgEnum.nextElement());
            }
        }

        return errorMessageList;
    }

    private void log(Category log, String message, int type) {
        switch (type) {
            case 0:
                log.debug(message);
                break;
            case 1:
            default:
                log.info(message);
                break;
            case 2:
                log.warn(message);
                break;
            case 3:
                log.error(message);
        }

    }

    public String toJsonArray() {
        Enumeration<Integer> errorEnum = this.getErrorMsgCodeEnum();
        String msg = null;
        Enumeration<String> msgEnum = null;
        int err = false;
        Integer errCd = null;
        StringBuffer buff = new StringBuffer();
        buff.append("[");

        while(errorEnum.hasMoreElements()) {
            errCd = (Integer)errorEnum.nextElement();
            int err = errCd;
            msgEnum = this.getErrorMsgMultiple(err);

            while(msgEnum.hasMoreElements()) {
                msg = (String)msgEnum.nextElement();
                buff.append("{");
                buff.append("\"errorCode\": " + err + ",");
                buff.append("\"errorMsg\": \"" + msg + "\"");
                buff.append("}");
                if (msgEnum.hasMoreElements()) {
                    buff.append(", \n");
                }
            }

            if (errorEnum.hasMoreElements()) {
                buff.append(", \n");
            }
        }

        buff.append("]");
        return buff.toString();
    }

    public void removeErrorMsgs(int code) {
        this.errorMsg.remove(code);
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
