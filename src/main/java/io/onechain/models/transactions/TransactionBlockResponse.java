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


import io.onechain.models.events.OneChainEvent;
import io.onechain.models.objects.BalanceChange;
import io.onechain.models.objects.ObjectChange;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * The type Transaction response.
 *
 * @author chiyu
 * @since 2022.11
 */
public class TransactionBlockResponse {

    private String digest;

    private TransactionBlock transaction;

    private TransactionBlockEffects effects;

    private List<OneChainEvent> events;

    @SuppressWarnings("checkstyle:MemberName")
    private BigInteger timestampMs;

    private BigInteger checkpoint;

    private boolean confirmedLocalExecution;

    private List<ObjectChange> objectChanges;

    private List<BalanceChange> balanceChanges;

    private List<String> errors;

    /**
     * Gets digest.
     *
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Sets digest.
     *
     * @param digest the digest
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * Gets transaction.
     *
     * @return the transaction
     */
    public TransactionBlock getTransaction() {
        return transaction;
    }

    /**
     * Sets transaction.
     *
     * @param transaction the transaction
     */
    public void setTransaction(TransactionBlock transaction) {
        this.transaction = transaction;
    }

    /**
     * Gets effects.
     *
     * @return the effects
     */
    public TransactionBlockEffects getEffects() {
        return effects;
    }

    /**
     * Sets effects.
     *
     * @param effects the effects
     */
    public void setEffects(TransactionBlockEffects effects) {
        this.effects = effects;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public List<OneChainEvent> getEvents() {
        return events;
    }

    /**
     * Sets events.
     *
     * @param events the events
     */
    public void setEvents(List<OneChainEvent> events) {
        this.events = events;
    }

    /**
     * Gets timestamp ms.
     *
     * @return the timestamp ms
     */
    public BigInteger getTimestampMs() {
        return timestampMs;
    }

    /**
     * Sets timestamp ms.
     *
     * @param timestampMs the timestamp ms
     */
    public void setTimestampMs(BigInteger timestampMs) {
        this.timestampMs = timestampMs;
    }

    /**
     * Gets checkpoint.
     *
     * @return the checkpoint
     */
    public BigInteger getCheckpoint() {
        return checkpoint;
    }

    /**
     * Sets checkpoint.
     *
     * @param checkpoint the checkpoint
     */
    public void setCheckpoint(BigInteger checkpoint) {
        this.checkpoint = checkpoint;
    }

    /**
     * Is confirmed local execution boolean.
     *
     * @return the boolean
     */
    public boolean isConfirmedLocalExecution() {
        return confirmedLocalExecution;
    }

    /**
     * Sets confirmed local execution.
     *
     * @param confirmedLocalExecution the confirmed local execution
     */
    public void setConfirmedLocalExecution(boolean confirmedLocalExecution) {
        this.confirmedLocalExecution = confirmedLocalExecution;
    }

    /**
     * Gets object changes.
     *
     * @return the object changes
     */
    public List<ObjectChange> getObjectChanges() {
        return objectChanges;
    }

    /**
     * Sets object changes.
     *
     * @param objectChanges the object changes
     */
    public void setObjectChanges(List<ObjectChange> objectChanges) {
        this.objectChanges = objectChanges;
    }

    public List<BalanceChange> getBalanceChanges() {
        return balanceChanges;
    }

    public void setBalanceChanges(List<BalanceChange> balanceChanges) {
        this.balanceChanges = balanceChanges;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Sets errors.
     *
     * @param errors the errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionBlockResponse)) {
            return false;
        }
        TransactionBlockResponse that = (TransactionBlockResponse) o;
        return confirmedLocalExecution == that.confirmedLocalExecution
                && digest.equals(that.digest)
                && transaction.equals(that.transaction)
                && effects.equals(that.effects)
                && events.equals(that.events)
                && timestampMs.equals(that.timestampMs)
                && checkpoint.equals(that.checkpoint)
                && objectChanges.equals(that.objectChanges)
                && balanceChanges.equals(that.balanceChanges)
                && errors.equals(that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                digest,
                transaction,
                effects,
                events,
                timestampMs,
                checkpoint,
                confirmedLocalExecution,
                objectChanges,
                balanceChanges,
                errors);
    }

    @Override
    public String toString() {
        return "TransactionResponse{"
                + "digest='"
                + digest
                + '\''
                + ", transaction="
                + transaction
                + ", effects="
                + effects
                + ", events="
                + events
                + ", timestampMs="
                + timestampMs
                + ", checkpoint="
                + checkpoint
                + ", confirmedLocalExecution="
                + confirmedLocalExecution
                + ", objectChanges="
                + objectChanges
                + ", balanceChanges="
                + balanceChanges
                + ", errors="
                + errors
                + '}';
    }
}
