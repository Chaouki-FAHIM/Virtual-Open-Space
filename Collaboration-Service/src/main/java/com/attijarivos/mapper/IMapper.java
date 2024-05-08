package com.attijarivos.mapper;

public interface IMapper <Model,Request,Response>{
    Model fromReqToModel(Request request);
    Response fromModelToRes(Model model);
}
