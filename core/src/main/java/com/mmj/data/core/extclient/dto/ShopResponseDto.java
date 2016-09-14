package com.mmj.data.core.extclient.dto;

import java.util.ArrayList;
import java.util.List;

public class ShopResponseDto extends BaseResponseDto {

    private static final long serialVersionUID = 677867625038983413L;

    private List<ShopDto> shopDtos;

    public ShopResponseDto() {
    }

    public List<ShopDto> getShopDtos() {
        if (null == shopDtos) {
            shopDtos = new ArrayList<ShopDto>();
        }
        return shopDtos;
    }

    public void setShopDtos(List<ShopDto> shopDtos) {
        this.shopDtos = shopDtos;
    }

    public void add(ShopDto shopDto) {
        getShopDtos().add(shopDto);
    }

}
