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

package io.onechain.models.transactions;


import io.onechain.models.objects.OneChainObjectRef;
import io.onechain.models.objects.OneChainOwnerObjectRef;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * The type Transaction effects.
 *
 * @author chiyu
 * @since 2022.11
 */
public class TransactionBlockEffects {

  private String messageVersion = "v1";

  private ExecutionStatus status;

  private BigInteger executedEpoch;

  private List<TransactionBlockEffectsModifiedAtVersions> modifiedAtVersions;

  private GasCostSummary gasUsed;

  private List<OneChainObjectRef> sharedObjects;

  private String transactionDigest;

  private List<OneChainOwnerObjectRef> created;

  private List<OneChainOwnerObjectRef> mutated;

  private List<OneChainOwnerObjectRef> unwrapped;

  private List<OneChainObjectRef> deleted;

  private List<OneChainObjectRef> wrapped;

  @SuppressWarnings("checkstyle:MemberName")
  private List<OneChainObjectRef> unwrapped_then_deleted;

  private OneChainOwnerObjectRef gasObject;

  private String eventsDigest;

  private List<String> dependencies;

  /**
   * Gets message version.
   *
   * @return the message version
   */
  public String getMessageVersion() {
    return messageVersion;
  }

  /**
   * Sets message version.
   *
   * @param messageVersion the message version
   */
  public void setMessageVersion(String messageVersion) {
    this.messageVersion = messageVersion;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public ExecutionStatus getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(ExecutionStatus status) {
    this.status = status;
  }

  /**
   * Gets executed epoch.
   *
   * @return the executed epoch
   */
  public BigInteger getExecutedEpoch() {
    return executedEpoch;
  }

  /**
   * Sets executed epoch.
   *
   * @param executedEpoch the executed epoch
   */
  public void setExecutedEpoch(BigInteger executedEpoch) {
    this.executedEpoch = executedEpoch;
  }

  /**
   * Gets gas used.
   *
   * @return the gas used
   */
  public GasCostSummary getGasUsed() {
    return gasUsed;
  }

  /**
   * Sets gas used.
   *
   * @param gasUsed the gas used
   */
  public void setGasUsed(GasCostSummary gasUsed) {
    this.gasUsed = gasUsed;
  }

  /**
   * Gets shared objects.
   *
   * @return the shared objects
   */
  public List<OneChainObjectRef> getSharedObjects() {
    return sharedObjects;
  }

  /**
   * Sets shared objects.
   *
   * @param sharedObjects the shared objects
   */
  public void setSharedObjects(List<OneChainObjectRef> sharedObjects) {
    this.sharedObjects = sharedObjects;
  }

  /**
   * Gets transaction digest.
   *
   * @return the transaction digest
   */
  public String getTransactionDigest() {
    return transactionDigest;
  }

  /**
   * Sets transaction digest.
   *
   * @param transactionDigest the transaction digest
   */
  public void setTransactionDigest(String transactionDigest) {
    this.transactionDigest = transactionDigest;
  }

  /**
   * Gets created.
   *
   * @return the created
   */
  public List<OneChainOwnerObjectRef> getCreated() {
    return created;
  }

  /**
   * Sets created.
   *
   * @param created the created
   */
  public void setCreated(List<OneChainOwnerObjectRef> created) {
    this.created = created;
  }

  /**
   * Gets mutated.
   *
   * @return the mutated
   */
  public List<OneChainOwnerObjectRef> getMutated() {
    return mutated;
  }

  /**
   * Sets mutated.
   *
   * @param mutated the mutated
   */
  public void setMutated(List<OneChainOwnerObjectRef> mutated) {
    this.mutated = mutated;
  }

  /**
   * Gets unwrapped.
   *
   * @return the unwrapped
   */
  public List<OneChainOwnerObjectRef> getUnwrapped() {
    return unwrapped;
  }

  /**
   * Sets unwrapped.
   *
   * @param unwrapped the unwrapped
   */
  public void setUnwrapped(List<OneChainOwnerObjectRef> unwrapped) {
    this.unwrapped = unwrapped;
  }

  /**
   * Gets deleted.
   *
   * @return the deleted
   */
  public List<OneChainObjectRef> getDeleted() {
    return deleted;
  }

  /**
   * Sets deleted.
   *
   * @param deleted the deleted
   */
  public void setDeleted(List<OneChainObjectRef> deleted) {
    this.deleted = deleted;
  }

  /**
   * Gets wrapped.
   *
   * @return the wrapped
   */
  public List<OneChainObjectRef> getWrapped() {
    return wrapped;
  }

  /**
   * Sets wrapped.
   *
   * @param wrapped the wrapped
   */
  public void setWrapped(List<OneChainObjectRef> wrapped) {
    this.wrapped = wrapped;
  }

  /**
   * Gets unwrapped then deleted.
   *
   * @return the unwrapped then deleted
   */
  public List<OneChainObjectRef> getUnwrapped_then_deleted() {
    return unwrapped_then_deleted;
  }

  /**
   * Sets unwrapped then deleted.
   *
   * @param unwrapped_then_deleted the unwrapped then deleted
   */
  @SuppressWarnings("checkstyle:ParameterName")
  public void setUnwrapped_then_deleted(List<OneChainObjectRef> unwrapped_then_deleted) {
    this.unwrapped_then_deleted = unwrapped_then_deleted;
  }

  /**
   * Gets gas object.
   *
   * @return the gas object
   */
  public OneChainOwnerObjectRef getGasObject() {
    return gasObject;
  }

  /**
   * Sets gas object.
   *
   * @param gasObject the gas object
   */
  public void setGasObject(OneChainOwnerObjectRef gasObject) {
    this.gasObject = gasObject;
  }

  /**
   * Gets events digest.
   *
   * @return the events digest
   */
  public String getEventsDigest() {
    return eventsDigest;
  }

  /**
   * Sets events digest.
   *
   * @param eventsDigest the events digest
   */
  public void setEventsDigest(String eventsDigest) {
    this.eventsDigest = eventsDigest;
  }

  /**
   * Gets dependencies.
   *
   * @return the dependencies
   */
  public List<String> getDependencies() {
    return dependencies;
  }

  /**
   * Sets dependencies.
   *
   * @param dependencies the dependencies
   */
  public void setDependencies(List<String> dependencies) {
    this.dependencies = dependencies;
  }

  public List<TransactionBlockEffectsModifiedAtVersions> getModifiedAtVersions() {
    return modifiedAtVersions;
  }

  public void setModifiedAtVersions(
      List<TransactionBlockEffectsModifiedAtVersions> modifiedAtVersions) {
    this.modifiedAtVersions = modifiedAtVersions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TransactionBlockEffects)) {
      return false;
    }
    TransactionBlockEffects that = (TransactionBlockEffects) o;
    return messageVersion.equals(that.messageVersion)
        && status.equals(that.status)
        && executedEpoch.equals(that.executedEpoch)
        && modifiedAtVersions.equals(that.modifiedAtVersions)
        && gasUsed.equals(that.gasUsed)
        && sharedObjects.equals(that.sharedObjects)
        && transactionDigest.equals(that.transactionDigest)
        && created.equals(that.created)
        && mutated.equals(that.mutated)
        && unwrapped.equals(that.unwrapped)
        && deleted.equals(that.deleted)
        && wrapped.equals(that.wrapped)
        && unwrapped_then_deleted.equals(that.unwrapped_then_deleted)
        && gasObject.equals(that.gasObject)
        && eventsDigest.equals(that.eventsDigest)
        && dependencies.equals(that.dependencies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        messageVersion,
        status,
        executedEpoch,
        modifiedAtVersions,
        gasUsed,
        sharedObjects,
        transactionDigest,
        created,
        mutated,
        unwrapped,
        deleted,
        wrapped,
        unwrapped_then_deleted,
        gasObject,
        eventsDigest,
        dependencies);
  }

  @Override
  public String toString() {
    return "TransactionBlockEffects{"
        + "messageVersion='"
        + messageVersion
        + '\''
        + ", status="
        + status
        + ", executedEpoch="
        + executedEpoch
        + ", modifiedAtVersions="
        + modifiedAtVersions
        + ", gasUsed="
        + gasUsed
        + ", sharedObjects="
        + sharedObjects
        + ", transactionDigest='"
        + transactionDigest
        + '\''
        + ", created="
        + created
        + ", mutated="
        + mutated
        + ", unwrapped="
        + unwrapped
        + ", deleted="
        + deleted
        + ", wrapped="
        + wrapped
        + ", unwrapped_then_deleted="
        + unwrapped_then_deleted
        + ", gasObject="
        + gasObject
        + ", eventsDigest='"
        + eventsDigest
        + '\''
        + ", dependencies="
        + dependencies
        + '}';
  }
}
