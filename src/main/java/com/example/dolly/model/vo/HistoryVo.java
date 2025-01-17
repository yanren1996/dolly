package com.example.dolly.model.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HistoryVo {
    private List<MessageVo> Content;
    private boolean HasNext;
}
