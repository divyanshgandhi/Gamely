package com.stimuligaming.gamely.helperClasses;

public class Message {
    private String content, image;
    private boolean isLeft;

    public Message(String content, String image, boolean isLeft) {
        this.content = content;
        this.image = image;
        this.isLeft = isLeft;
    }

    public String getContent() {
        return content;
    }

    public String getImage(){
        return image;
    }

    public boolean getIsLeft(){
        return isLeft;
    }
}
