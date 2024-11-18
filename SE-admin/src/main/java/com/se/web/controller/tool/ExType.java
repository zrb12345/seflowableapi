package com.se.web.controller.tool;

import java.util.ArrayList;
import java.util.List;

public class ExType {

    private List<String> exTypes;
    public ExType(){
        exTypes = new ArrayList<>();
        exTypes.add("二级岩石乳化炸药");
        exTypes.add("岩石膨化硝铵炸药");
        exTypes.add("铵梯炸药");
        exTypes.add("浆状炸药");
        exTypes.add("粉状铵油炸药");
        exTypes.add("多孔粒状铵油炸药");
        exTypes.add("重铵油炸药");
        exTypes.add("1#混装乳化炸药");
        exTypes.add("2#混装乳化炸药");
        exTypes.add("3#混装乳化炸药");
        exTypes.add("4#混装乳化炸药");
        exTypes.add("5#混装乳化炸药");
        exTypes.add("6#混装乳化炸药");
    }
    public List<String> getExTypes()
    {
        return exTypes;

    }
}
