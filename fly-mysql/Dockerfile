FROM mysql:8.0
# Bind MySQL to all interfaces (incl. IPv6) so it's reachable over Fly's
# private network (*.internal is IPv6-only — the default IPv4-only bind fails).
COPY my.cnf /etc/mysql/conf.d/zz-fly.cnf
