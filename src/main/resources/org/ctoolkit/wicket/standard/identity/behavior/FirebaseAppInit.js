// Initialize Firebase
var config = {
    apiKey: '${apiKey}',
    authDomain: '${projectId}.firebaseapp.com',
    databaseURL: 'https://${databaseName}.firebaseio.com',
    projectId: '${projectId}',
    storageBucket: '${bucketName}.appspot.com',
    messagingSenderId: '${senderId}'
};
firebase.initializeApp( config );
