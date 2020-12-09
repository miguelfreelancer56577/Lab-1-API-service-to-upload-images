package com.github.mangelt.lab1.component;

import com.github.mangelt.lab1.domain.ImageDetailsPayload;

public interface ImageValidator {
	boolean isValid(ImageDetailsPayload image);
}
