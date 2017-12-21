#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <pwd.h>
#include <netinet/in.h>
#include <time.h>
#include <dirent.h>

#define BSIZE 1024

// define the ftp server root

typedef struct Port {
    int port1;
    int port2;
}Port;

typedef struct State {
    // define the connection mode: normal, server and client
    int mode;
    // define whether is login mode
    int logged_in;
    // define whether username is allowed
    int usernameOk;
    char* username;

    // response message to client
    char* message;
    // define the command connection
    int connection;

    // passive connection
    int sock_passive;
    // data transfer id
    int transfer_id;
}State;

// command struct
typedef struct Command {
    char command[5];
    char arg[BSIZE];
}Command;

/*
 * connection mode
 * NORMAL - normal connection mode, nothing to transfer
 * SERVER - passive connection (PASV command), server listens
 * CLIENT - server connects to client(PORT command)
 */
typedef enum conn_mode{NORMAL, SERVER, CLIENT} conn_mode;

/*
 * commands enumeration
 */
typedef enum commandList {
    ABOR,
    CWD,
    DELE,
    LIST,
    MDTM,
    MKD,
    NLST,
    PASS,
    PASV,
    PORT,
    PWD,
    QUIT,
    RETR,
    RMD,
    RNFR,
    RNTO,
    SITE,
    SIZE,
    STOR,
    TYPE,
    USER,
    NOOP
}commandList;

/*
 * String mappings for commandList
 */
static const char* commandList_str[] = {
    "ABOR",
    "CWD",
    "DELE",
    "LIST",
    "MDTM",
    "MKD",
    "NLST",
    "PASS",
    "PASV",
    "PORT",
    "PWD",
    "QUIT",
    "RETR",
    "RMD",
    "RNFR",
    "RNTO",
    "SITE",
    "SIZE",
    "STOR",
    "TYPE",
    "USER",
    "NOOP"
};

/*valid usernames for ftp*/
static const char* username[] = {
    "ftp",
    "public",
    "anon",
    "test",
    "foo",
    "siim",
    "anonymous"
};

/*welcome message*/
static char* welcome_message = "Welcome to the ftp server";

/*for server functions*/
void getPort(Port*);
void parseCommand(char*, Command*);

// create a socket by the operator kenal
int createSocket(int port);
void writeState(State*);
int acceptConnection(int);

// commands handle functions
void response(Command*, State*);
void ftpUser(Command*, State*);
void ftpPass(Command*, State*);
void ftpPwd(Command*, State*);
void ftpCwd(Command*, State*);
void ftpMkd(Command*, State*);
void ftpRmd(Command*, State*);
void ftpPasv(Command*, State*);
void ftpList(Command*, State*);
void ftpRetr(Command*, State*);
void ftpStor(Command*, State*);
void ftpDele(Command*, State*);
void ftpSize(Command*, State*);
void ftpQuit(State*);
void ftpType(Command*, State*);
void ftpAbor(State*);

void strPerm(int, char*);
void myWait(int);
