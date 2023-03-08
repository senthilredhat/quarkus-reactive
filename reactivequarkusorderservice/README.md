# Reactive Orders project demo

This is a quarkus, reactive, GRPC project used to demonstrate issues with Database Credentials Provider.

This implementation uses a custom credentials' provider(MyCredentialsProvider.java) to feed username and password for the database. There is a rest endpoint to set the new credentials as well.

Please follow [setup-running.md](./setup-running.md) for detailed instructions on setting up and reproducing the problem