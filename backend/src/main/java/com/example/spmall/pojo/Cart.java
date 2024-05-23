package com.example.spmall.pojo;

import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Cart
 *
 * @author toy aa@nfda
 * @version 2024/04/11 9:32 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
  private int id;
  private int userId;
  private int skuId;
  private int qty;
  private SkuInfo skuInfo;
  private LocalDateTime createDate;
}
