package com.ggarr.www.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequest implements Serializable {

    private static final long serialVersionUID = 2214371311574386297L;

    private Integer targetPostIdx;
    private Integer targetCommentIdx;
    private String content;
}
