#include "common.h"
#include "server.h"
#include <pthread.h>
#include <string.h>

void* communication(void* _C) {
    chdir("/");
    int connection = *(int*)_C, bytes_read;
    char buffer[BSIZE];
    Command* command = malloc(sizeof(Command));
    State* state = malloc(sizeof(State));
    state->username = NULL;
    memset(buffer, 0, BSIZE);
    char welcome[BSIZE] = "220";
    if (strlen(welcome_message) < BSIZE-4) {
        strcat(welcome, welcome_message);
    } else {
        strcat(welcome, "welcome to nice FTP service");
    }

    /*write welcome message*/
    strcat(welcome, "\n");
    write(connection, welcome, strlen(welcome));

    /*read commands from client*/
    while (bytes_read == read(connection, buffer, BSIZE)) {
        if (!(bytes_read > BSIZE) && bytes_read > 0) {
            printf("User %s sent command: %s\n",(state->username==0)?"unknown":state->username,buffer);
            parseCommand(buffer, command);
            state->connection = connection;

            /* Ignore non-ascii char. Ignores telnet command */
            if(buffer[0]<=127 || buffer[0]>=0){
                response(command,state);
            }
            memset(buffer,0,BSIZE);
            memset(command,0,sizeof(command)));
        }
    }
    close(connection);
    printf("Client disconnected!");
    return NULL;
}

/*
 * handle up server and handle incoming connections
 */
void server(int port) {
    int socket = createSocket(port);
    struct sockaddr_in clientAddress;
    int length = sizeof(clientAddress);
    while(1) {
        int* connection = (int *) malloc (sizeof(int));
        *connection = accept(socket, (struct sockaddr*)&clientAddress, &length);

        pthread_t pid;
        pthread_create(&pid, NULL, communication, (void*)(connection));
    }
}

void getPort(Port* port) {
    srand(time(NULL));
    port->port1 = 128 + (rand()%64);
    port->port2 = rand()%0xff;
}

void parseCommand(char *cmdstring, Command *cmd) {
    sscanf(cmdstring,"%s %s",cmd->command,cmd->arg);
}

int createSocket(int port) {
    int socket;
    int reuse = 1;
    struct sockaddr_in serverAddress = (struct sockaddr_in) {
        AF_INET, htons(port), (struct in_addr){INADDR_ANY}
    };
    if ((socket = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        fprintf(stderr, "the Server can't open a socket");
        exit(EXIT_FAILURE);
    }
    setsockopt(socket, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse));

    if (bind(socket, (struct sockaddr*)&serverAddress, sizeof(serverAddress)) < 0) {
        fprintf(stderr, "can't bind socket to address");
        exit(EXIT_FAILURE);
    }
    listen(socket, 5);
    return socket;
}

void writeState(State *state) {
    write(state->connection, state->message, strlen(state->message));
}

int acceptConnection(int socket) {
    int addressLength = 0;
    struct sockaddr_in clientAddress;
    addressLength = sizeof(clientAddress);
    return accept(socket, (struct sockaddr*)&clientAddress, &addressLength);
}

void my_wait(int signum) {
    int status;
    wait(&status);
}

int lookup(char* needle, const char **haystack, int count) {
    int i;
    for (i = 0; i < count; i++) {
        if (strcmp(needle, haystack[i]) == 0) return i;
    }
    return -1;
}

int lookup_cmd(char * cmd) {
    const int commandListCount = sizeof(commandList_str)/sizeof(char*);
    return lookup(cmd, commandList_str, commandListCount);
}