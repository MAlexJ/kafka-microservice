package com.malex.filteringservice.model.request;

import com.malex.filteringservice.model.filter.FilterCondition;

public record FilterRequest(FilterCondition condition) {}
