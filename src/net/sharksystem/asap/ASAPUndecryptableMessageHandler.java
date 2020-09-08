package net.sharksystem.asap;

import java.util.BitSet;

public interface ASAPUndecryptableMessageHandler {
    String FORMAT_UNDECRYPTABLE_MESSAGES = "asap/undecryptable";
    String URI_UNDECRYPTABLE_MESSAGES = "asap://undecryptable";
    /**
     * Peer can (and should) receive encrypted messages without being receiver. A peer is not able
     * to encrypt that message but could store and forward. That is what ASAP is about.
     */
    void handleUndecryptableMessage(byte[] encryptedMessage, CharSequence receiver);
}
