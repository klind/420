package com.mmj.data.core.extclient.dto;

public class ShopFlightResponseDto extends BaseResponseDto {

    private static final long serialVersionUID = 1396090464629528346L;
    private ShopDto shopDto;

    public ShopFlightResponseDto() {
    }

    public ShopDto getShopDto() {
        return shopDto;
    }

    public void setShopDto(ShopDto shopDto) {
        this.shopDto = shopDto;
    }

}
