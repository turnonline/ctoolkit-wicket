package org.ctoolkit.wicket.turnonline.identity;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Firebase configuration options value holder.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IdentityOptions
        implements Serializable
{
    private static final long serialVersionUID = 649739600329865627L;

    private String signInSuccessUrl;

    private String termsUrl;

    private String credentialHelper = "firebaseui.auth.CredentialHelper.NONE";

    private String apiKey;

    private String projectId;

    private String databaseName;

    private String bucketName;

    private String senderId;

    private List<String> signInOptions = new ArrayList<>();

    public IdentityOptions()
    {
    }

    /**
     * The URL (relative) where to redirect the user after a successful sign-in.
     * Required when the signInSuccess callback is not used or when it returns true.
     *
     * @return the where to redirect URL
     */
    public String getSignInSuccessUrl()
    {
        return signInSuccessUrl;
    }

    public void setSignInSuccessUrl( String signInSuccessUrl )
    {
        this.signInSuccessUrl = signInSuccessUrl;
    }

    /**
     * The URL (relative) of the terms of service page.
     *
     * @return the relative terms path
     */
    public String getTermsUrl()
    {
        return termsUrl;
    }

    public void setTermsUrl( String termsUrl )
    {
        this.termsUrl = termsUrl;
    }

    /**
     * The credential helper configuration whether to use accountchooser.com or not.
     *
     * @return the credential helper
     */
    public String getCredentialHelper()
    {
        return credentialHelper;
    }

    /**
     * Possible values:
     * firebaseui.auth.CredentialHelper.NONE
     * firebaseui.auth.CredentialHelper.ACCOUNT_CHOOSER_COM
     *
     * @param credentialHelper the one of the possible value to be set
     */
    public void setCredentialHelper( String credentialHelper )
    {
        this.credentialHelper = credentialHelper;
    }

    /**
     * The Firebase API key.
     *
     * @return the api key
     */
    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey( String apiKey )
    {
        this.apiKey = apiKey;
    }

    /**
     * The list of providers enabled for signing into your app.
     *
     * @return the list of providers
     */
    public List<String> getSignInOptions()
    {
        return signInOptions;
    }

    /**
     * The order you specify them will be the order they are displayed
     * on the sign-in provider selection screen.
     *
     * @param signInOptions the list of sign in options to be set
     */
    public void setSignInOptions( List<String> signInOptions )
    {
        if ( signInOptions == null )
        {
            this.signInOptions.clear();
            return;
        }
        this.signInOptions = signInOptions;
    }

    /**
     * The list of comma separated providers to be rendered as a string.
     *
     * @return the list of comma separated providers
     */
    public String getSignInOptionsAsString()
    {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = signInOptions.iterator();

        while ( iterator.hasNext() )
        {
            String signInOption = iterator.next();
            if ( Strings.isNullOrEmpty( signInOption ) )
            {
                continue;
            }

            builder.append( signInOption );
            if ( iterator.hasNext() )
            {
                builder.append( "," );
                builder.append( "\n" );
            }
        }

        return builder.toString();
    }

    /**
     * The project ID, know as App Engine application ID.
     *
     * @return the project id
     */
    public String getProjectId()
    {
        return projectId;
    }

    /**
     * Rendered as authDomain ${projectId}.firebaseapp.com and projectId ${projectId}.
     *
     * @param projectId the project ID to be set
     */
    public void setProjectId( String projectId )
    {
        this.projectId = projectId;
    }

    /**
     * The Firebase database name.
     *
     * @return the database name
     */
    public String getDatabaseName()
    {
        return databaseName;
    }

    /**
     * Rendered as databaseURL https://${databaseName}.firebaseio.com
     *
     * @param databaseName the database name to be set
     */
    public void setDatabaseName( String databaseName )
    {
        this.databaseName = databaseName;
    }

    /**
     * The Firebase store bucket name.
     *
     * @return the store bucket name
     */
    public String getBucketName()
    {
        return bucketName;
    }

    /**
     * Rendered as storageBucket ${bucketName}.appspot.com
     *
     * @param bucketName the bucket name to be set
     */
    public void setBucketName( String bucketName )
    {
        this.bucketName = bucketName;
    }

    /**
     * The Firebase sender Id.
     *
     * @return the sender id
     */
    public String getSenderId()
    {
        return senderId;
    }

    /**
     * Rendered as messagingSenderId ${senderId}.
     *
     * @param senderId the sender ID to be set
     */
    public void setSenderId( String senderId )
    {
        this.senderId = senderId;
    }
}