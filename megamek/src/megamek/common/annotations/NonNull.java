/*
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/
 */
package megamek.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * When used on a method, this annotation denotes that the method may not a null value by design. As
 * a result, consumers of that value should never be designed to handle null values, which can only
 * occur from previous system failures.
 *
 * When used on a parameter, this annotation denotes that the parameter may never contain a null
 * value. As a result, any overriding methods never need to handle null values for this parameter.
 */
@Target(value = {ElementType.METHOD, ElementType.PARAMETER})
@Documented
@Inherited
public @interface NonNull {

}
