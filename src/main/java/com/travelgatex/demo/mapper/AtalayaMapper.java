package com.travelgatex.demo.mapper;

import com.travelgatex.demo.api.data.HotelResponse;
import com.travelgatex.demo.service.data.HotelesAtalaya;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AtalayaMapper {
    HotelResponse atalayaHotelToHotel(HotelesAtalaya hotel);
}
