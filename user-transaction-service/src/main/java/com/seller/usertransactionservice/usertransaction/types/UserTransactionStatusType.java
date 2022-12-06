package com.seller.usertransactionservice.usertransaction.types;

import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class UserTransactionStatusType extends EnumType<UserTransactionStatus> {

  @Override
  public void nullSafeSet(
      PreparedStatement ps, Object obj, int index, SharedSessionContractImplementor session
  ) throws HibernateException, SQLException {
    if (obj == null) {
      ps.setNull(index, Types.OTHER);
    } else {
      ps.setObject(index, obj.toString(), Types.OTHER);
    }
  }
}