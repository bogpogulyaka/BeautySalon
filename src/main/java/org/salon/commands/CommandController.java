package org.salon.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandController {
    boolean execute(HttpServletRequest req, HttpServletResponse resp);
}