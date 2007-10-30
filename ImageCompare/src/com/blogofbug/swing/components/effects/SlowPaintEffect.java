/*
 * SlowPaintEffect.java
 *
 * Created on March 31, 2007, 1:18 PM
 *
 * Copyright 2006-2007 Nigel Hughes
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at http://www.apache.org/
 * licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.blogofbug.swing.components.effects;

/**
 * Some effects take a long time to paint and should be able to not paint until
 * some part of another program decides they should be painted. 
 *
 * @author nigel
 */
public interface SlowPaintEffect {
    /**
     * Allows a single call to initializeEffect to be made
     *
     */
    public void removePaintLock();
}
