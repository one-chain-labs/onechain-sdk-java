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

package io.onechain.models;


import io.onechain.jsonrpc.JsonRpc20Response;

/**
 * The type Sui api exception.
 *
 * @author chiyu
 * @since 2022.11
 */
public class OneChainApiException extends Exception {

  private JsonRpc20Response.Error error;

  /**
   * Instantiates a new Sui api exception.
   *
   * @param error the error
   */
  public OneChainApiException(JsonRpc20Response.Error error) {
    super();
    this.error = error;
  }

  public OneChainApiException(Throwable cause) {
    super(cause);
  }

  /**
   * Gets error.
   *
   * @return the error
   */
  public JsonRpc20Response.Error getError() {
    return error;
  }

  public void setError(JsonRpc20Response.Error error) {
    this.error = error;
  }
}
