package net.sharksystem.asap;

import net.sharksystem.asap.crypto.ASAPPoint2PointCryptoSettings;
import net.sharksystem.asap.engine.ASAPInternalOnlinePeersChangedListener;
import net.sharksystem.asap.engine.ASAPInternalPeer;
import net.sharksystem.asap.protocol.ASAPConnection;
import net.sharksystem.asap.utils.PeerIDHelper;
import net.sharksystem.utils.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public abstract class ASAPInternalPeerWrapper extends ASAPListenerManagingPeer
        implements ASAPInternalOnlinePeersChangedListener {

    private ASAPInternalPeer peer;

    protected void setInternalPeer(ASAPInternalPeer peer) {
        this.peer = peer;
        this.peer.addOnlinePeersChangedListener(this);

        this.log("activate online messages on that peer");
        this.peer.activateOnlineMessages();
    }


    @Override
    public boolean isASAPRoutingAllowed(CharSequence applicationFormat) throws IOException, ASAPException {
        return this.getInternalPeer().asapRoutingAllowed(applicationFormat);
    }

    @Override
    public void setASAPRoutingAllowed(CharSequence applicationFormat, boolean allowed)
            throws IOException, ASAPException {
        
        this.getInternalPeer().setAsapRoutingAllowed(applicationFormat, allowed);
    }

    protected ASAPInternalPeer getInternalPeer() {
        return this.peer;
    }

    public CharSequence getPeerID() {
        return this.peer.getOwner();
    }

    public boolean samePeer(ASAPPeer otherPeer) {
        return this.samePeer(otherPeer.getPeerID());
    }

    public boolean samePeer(CharSequence otherPeerID) {
        return PeerIDHelper.sameID(this.getPeerID(), otherPeerID);
    }

    @Override
    public ASAPStorage getASAPStorage(CharSequence format) throws IOException, ASAPException {
        return this.getInternalPeer().getASAPEngine(format);
    }

    public ASAPConnection handleConnection(InputStream is, OutputStream os) throws IOException, ASAPException {
        return this.peer.handleConnection(is, os);
    }

    public ASAPConnection handleConnection(
            InputStream is, OutputStream os,
            boolean encrypt, boolean sign,
            Set<CharSequence> appsWhiteList, Set<CharSequence> appsBlackList
    ) throws IOException, ASAPException {
        return this.peer.handleConnection(is, os, encrypt, sign, appsWhiteList, appsBlackList);
    }

    @Override
    public void notifyOnlinePeersChanged(ASAPInternalPeer peer) {
        this.notifyOnlinePeersChanged(peer.getOnlinePeers());
    }

    public void notifyOnlinePeersChanged(Set<CharSequence> peerList) {
        this.environmentChangesListenerManager.notifyListeners(peerList);
    }
}
