/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.cloud.scheduler.ha;

import org.apache.shardingsphere.elasticjob.cloud.scheduler.mesos.SchedulerService;
import org.apache.shardingsphere.elasticjob.cloud.reg.base.ElectionCandidate;
import org.apache.shardingsphere.elasticjob.cloud.exception.JobSystemException;
import org.apache.shardingsphere.elasticjob.cloud.reg.base.CoordinatorRegistryCenter;

/**
 * 调度器选举候选人.
 *
 * @author caohao
 */
public final class SchedulerElectionCandidate implements ElectionCandidate {
    
    private final CoordinatorRegistryCenter regCenter;
    
    private SchedulerService schedulerService;
    
    public SchedulerElectionCandidate(final CoordinatorRegistryCenter regCenter) {
        this.regCenter = regCenter;
    }
    
    @Override
    public void startLeadership() throws Exception {
        try {
            schedulerService = new SchedulerService(regCenter);
            schedulerService.start();
            //CHECKSTYLE:OFF
        } catch (final Throwable throwable) {
            throw new JobSystemException(throwable);
        }
    }
    
    @Override
    public void stopLeadership() {
        schedulerService.stop();
    }
}
