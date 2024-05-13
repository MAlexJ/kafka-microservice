package com.malex.filteringservice.model.filter;

import java.util.List;

public record FilterCondition(ConditionType type, List<String> keyWords) {}
