package io.onechain.bcsgen;

import io.onechain.models.transactions.TypeTag;

import java.util.List;

/**
 * @author chiyu
 * @date 2023/7/11 10:12
 */

public class MoveCallParam {

    String packageObjectId;
    String module;
    String function;
    List<io.onechain.models.transactions.TypeTag> typeArguments;
    List<?> arguments;

    public MoveCallParam() {
    }

    public MoveCallParam(String packageObjectId, String module, String function, List<TypeTag> typeArguments, List<?> arguments) {
        this.packageObjectId = packageObjectId;
        this.module = module;
        this.function = function;
        this.typeArguments = typeArguments;
        this.arguments = arguments;
    }


    public String getPackageObjectId() {
        return packageObjectId;
    }

    public void setPackageObjectId(String packageObjectId) {
        this.packageObjectId = packageObjectId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<TypeTag> getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(List<TypeTag> typeArguments) {
        this.typeArguments = typeArguments;
    }

    public List<?> getArguments() {
        return arguments;
    }

    public void setArguments(List<?> arguments) {
        this.arguments = arguments;
    }
}

