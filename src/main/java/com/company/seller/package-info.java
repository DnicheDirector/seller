@GenericGenerator(name = "uuid", strategy = "uuid2")

@TypeDef(name = "role_enum", typeClass = RoleType.class)

package com.company.seller;

import com.company.seller.types.RoleType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;