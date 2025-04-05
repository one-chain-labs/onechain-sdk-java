/*
 * Copyright 2022-2023 281165273grape@gmail.com
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


import io.onechain.clients.OneChainObjectNotFoundException;
import java.util.Objects;

/**
 * The type Sui object response.
 *
 * @author chiyu
 * @since 2023.04
 */
public class OneChainObjectResponse {

  private OneChainObjectData data;

  private OneChainObjectResponseError error;

  /**
   * Gets data.
   *
   * @return the data
   */
  public OneChainObjectData getData() {
    return data;
  }

  /**
   * Sets data.
   *
   * @param data the data
   */
  public void setData(OneChainObjectData data) {
    this.data = data;
  }

  /**
   * Gets error.
   *
   * @return the error
   */
  public OneChainObjectResponseError getError() {
    return error;
  }

  /**
   * Sets error.
   *
   * @param error the error
   */
  public void setError(OneChainObjectResponseError error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OneChainObjectResponse)) {
      return false;
    }
    OneChainObjectResponse that = (OneChainObjectResponse) o;
    return data.equals(that.data) && error.equals(that.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, error);
  }

  @Override
  public String toString() {
    return "SuiObjectResponse{" + "data=" + data + ", error=" + error + '}';
  }

  /**
   * Gets object ref.
   *
   * @return the object ref
   */
  public OneChainObjectRef getObjectRef() {
    if (this.error != null) {
      throw new OneChainObjectNotFoundException();
    }
    return this.getData().getRef();
  }
}
