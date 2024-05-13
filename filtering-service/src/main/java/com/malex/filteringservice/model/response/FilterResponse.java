package com.malex.filteringservice.model.response;

import com.malex.filteringservice.model.filter.FilterCondition;
import java.time.LocalDateTime;

public record FilterResponse(String id, FilterCondition condition, LocalDateTime created) {}
