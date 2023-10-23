package org.so;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyObject {
    private final String title;
    private final String code;
    private final int count;

    public MyObject(String title, String code, int count) {
        this.title = title;
        this.code = code;
        this.count = count;
    }
}
