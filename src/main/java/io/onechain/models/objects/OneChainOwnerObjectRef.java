/*
 * Copyright 2022 281165273grape@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.onechain.models.objects;


import java.util.Objects;

/**
 * The type Sui owner object ref.
 *
 * @author chiyu
 * @since 2022.11
 */
public class OneChainOwnerObjectRef {

  private OneChainObjectOwner owner;

  private OneChainObjectRef reference;

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public OneChainObjectOwner getOwner() {
    return owner;
  }

  /**
   * Sets owner.
   *
   * @param owner the owner
   */
  public void setOwner(OneChainObjectOwner owner) {
    this.owner = owner;
  }

  /**
   * Gets reference.
   *
   * @return the reference
   */
  public OneChainObjectRef getReference() {
    return reference;
  }

  /**
   * Sets reference.
   *
   * @param reference the reference
   */
  public void setReference(OneChainObjectRef reference) {
    this.reference = reference;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OneChainOwnerObjectRef that = (OneChainOwnerObjectRef) o;
    return owner.equals(that.owner) && reference.equals(that.reference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner, reference);
  }

  @Override
  public String toString() {
    return "SuiOwnerObjectRef{" + "owner=" + owner + ", reference=" + reference + '}';
  }
}
