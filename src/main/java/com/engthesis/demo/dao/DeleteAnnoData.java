package com.engthesis.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class DeleteAnnoData {

    private Long id;

    public DeleteAnnoData() {}

}
