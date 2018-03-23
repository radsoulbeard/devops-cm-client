package sap.prd.cmintegration.cli;

import static java.lang.String.format;
import static sap.prd.cmintegration.cli.Commands.Helpers.getCommandName;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cmclient.Transport;

import sap.prd.cmintegration.cli.TransportRetriever.BackendType;

/**
 * Command for retrieving the description of a transport.
 */
@CommandDescriptor(name="get-transport-description")
class GetTransportDescription extends TransportRelated {

    final static private Logger logger = LoggerFactory.getLogger(GetTransportDescription.class);
    GetTransportDescription(BackendType backendType, String host, String user, String password, String changeId, String transportId) {
        super(backendType, host, user, password, changeId, transportId);
    }

    @Override
    protected Predicate<Transport> getOutputPredicate() {
        return it -> {
                       String description = it.getDescription();
                       if(StringUtils.isBlank(description)) {
                           logger.debug(format("Description of transport '%s' is blank. Nothing will be emitted.", it.getTransportID()));
                           return false;
                       } else {
                           logger.debug(format("Description of transport '%s' is not blank. Description '%s' will be emitted.", it.getTransportID(), it.getDescription()));
                           System.out.println(description); 
                           return true;}
                       };
    }

    public final static void main(String[] args) throws Exception {
        logger.debug(format("%s called with arguments: '%s'.", GetTransportDescription.class.getSimpleName(), Commands.Helpers.getArgsLogString(args)));
        TransportRelated.main(GetTransportDescription.class, args,
                format("%s [-cID <changeId>]  -tID <transportId>", getCommandName(GetTransportDescription.class)),
                "Returns the description for the transport represented by <changeId>, <transportId>. ChangeId must not be provided for ABAP backends.");
    }
}
