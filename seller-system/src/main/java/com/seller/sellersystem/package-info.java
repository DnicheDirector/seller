@GenericGenerator(name = "uuid", strategy = "uuid2")

@TypeDef(name = "role_enum", typeClass = RoleType.class)

package com.seller.sellersystem;

import com.seller.sellersystem.types.RoleType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;