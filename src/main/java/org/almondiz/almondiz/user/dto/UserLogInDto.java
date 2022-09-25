package org.almondiz.almondiz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.user.entity.ProviderType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLogInDto
{
    private String providerUid;

    private ProviderType providerType;
}
