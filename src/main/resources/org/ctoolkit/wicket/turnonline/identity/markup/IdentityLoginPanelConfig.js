var config = {
    widgetUrl: "${widgetUrl}",
    signInSuccessUrl: "${signInSuccessUrl}",
    signOutUrl: "${signOutUrl}",
    oobActionUrl: "${oobActionUrl}",
    apiKey: "${apiKey}",
    siteName: "${siteName}",
    accountChooserEnabled: false,
    signInOptions: ${signInOptions},
    displayMode: "${displayMode}"
};
// The HTTP POST body should be escaped by the server to prevent XSS
window.google.identitytoolkit.start(
        '#gitkitWidgetDiv', // accepts any CSS selector
        config );
