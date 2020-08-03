# RScheduler

RScheduler is a job scheduler and management plugin built especially for Minecraft server Retrox.eu, against Spigot 1.12. 

## Permissions

- **rscheduler.schedule** - All plugins permissions

## Commands

- **/scheduler list** - List of jobs (colored with running/not running)
- **/scheduler start <job id>** - Starts the job
- **/scheduler stop <job id>** - Stops the job
- **/scheduler show <job id>** - Shows detailed information about the job

# PlaceholderAPI hook

- **%rscheduler_<job id>%** - Shows the remaining time for the job
