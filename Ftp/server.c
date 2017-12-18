#include "common.h"
#include "server.h"
#include <pthread.h>

void* communication(void* _C) {
    chdir("/");
    int connection = *(int*)_C, bytes_read;
    char buffer[BSIZE];
    Command* command = malloc(sizeof(Command));
    State* state = malloc(sizeof(State));
    state->username = NULL;
    memset();
}

