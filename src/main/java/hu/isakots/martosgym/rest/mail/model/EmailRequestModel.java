package hu.isakots.martosgym.rest.mail.model;

import java.io.Serializable;

public class EmailRequestModel implements Serializable {
    private String mailTo;
    private String topic;
    private String content;

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailRequestModel{" +
                "mailTo='" + mailTo + '\'' +
                ", topic='" + topic + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
