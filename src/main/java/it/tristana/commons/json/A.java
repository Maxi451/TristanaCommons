package it.tristana.commons.json;

public record A(@Json(ignore = true) short[] shorts, B[] bs, String str) {}
