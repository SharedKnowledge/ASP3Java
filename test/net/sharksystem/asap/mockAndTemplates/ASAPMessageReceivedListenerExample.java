package net.sharksystem.asap.mockAndTemplates;

import net.sharksystem.EncounterConnectionType;
import net.sharksystem.asap.ASAPMessages;
import net.sharksystem.asap.ASAPMessageReceivedListener;

import java.io.IOException;
import java.util.Iterator;

class ASAPMessageReceivedListenerExample implements ASAPMessageReceivedListener {
    private final String peerName;

    ASAPMessageReceivedListenerExample(String peerName) {
        this.peerName = peerName;
    }

    ASAPMessageReceivedListenerExample() {
        this(null);
    }

    @Override
    public void asapMessagesReceived(ASAPMessages messages,
                                     String senderE2E, // E2E part
                                     String senderPoint2Point, boolean verified, boolean encrypted, // Point2Point part
                                     EncounterConnectionType connectionType) throws IOException {

        CharSequence format = messages.getFormat();
        CharSequence uri = messages.getURI();
        if (peerName != null) {
            System.out.print(peerName);
        }

        System.out.println("asap message received (" + format + " | " + uri + "). size == " + messages.size());
        Iterator<byte[]> yourPDUIter = messages.getMessages();
        while (yourPDUIter.hasNext()) {
            TestUtils.deserializeExample(yourPDUIter.next());
        }
    }
}
