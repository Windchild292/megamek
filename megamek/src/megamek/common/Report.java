/*
 * MegaMek -
 * Copyright (C) 2000,2001,2002,2003,2004,2005 Ben Mazur (bmazur@sev.org)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megamek.common;

import megamek.MegaMek;
import megamek.common.annotations.Nullable;
import megamek.common.constants.MMConstants;
import megamek.common.enums.ReportType;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * This class defines a single server report. It holds information such as the
 * report ID, who the report is about, who should see the report, and some
 * formatting information.
 * <p>
 * Typically, the report will be created by the relevant section in the
 * <code>Server</code>, and added to the phase report vector. The actual text
 * of the report must also be added to the <i>report-messages.properties</i>
 * file.
 * <p>
 * Example:
 * <p>
 * <code>Report r = new Report(3455);\n
 * r.subject = entity.getId();\n
 * r.indent();\n
 * r.addDesc(entity);\n
 * r.add(6);\n
 * r.choose(true);\n
 * vPhaseReport.addElement(r);</code>
 * <p>
 * Then the following line would be added to <i>report-messages.properties</i>:
 * <p>
 * 3455::&lt;data&gt; (&lt;data&gt;) does &lt;data&gt; damage to the
 * &lt;msg:3456,3457&gt;.\n
 * 3456::tank\n
 * 3457::building
 * <p>
 * When the client parses the report, it will fill in the &lt;data&gt; tags with
 * the values that were given to the <code>add</code> methods called on the
 * report object.
 * <p>
 * The example above might produce a report such as this when the
 * <code>getText</code> method was called:
 * <p> " Crusader (Bob) does 6 damage to the tank."
 *
 * Note: some fields are marked transient because they are only used by the
 * server (or only the client). This shaves a few bytes off the packet size,
 * helping the dial-up people :)
 *
 * @author Ryan McConnell (oscarmm)
 */
public class Report implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = -5586008091586682078L;

    //region Constants
    private static final int MESSAGE_NONE = -1;
    
    /** Report Type: visible to all players. */
    public static final int PUBLIC = 0;
    
    /**
     * Report Type: visible to all players, but all data marked for obscuration
     * remains hidden. Note: Not used at this time, since all reports are
     * considered <code>obscured</code> unless explicitly marked
     * <code>public</code>.
     */
    public static final int OBSCURED = 1;
    
    /**
     * Report is only visible to those players who can see the subject. Note:
     * Not used at this time, since all reports are considered
     * <code>obscured</code> unless explicitly marked <code>public</code>.
     */
    public static final int HIDDEN = 2;
    
    /** Testing only - remove me later. */
    public static final int TESTING = 3;
    
    /**
     * Messages which should be sent only to the player indicated by "player"
     */
    public static final int PLAYER = 4;

    /**
     * The string that appears in the report to obscure certain information.
     */
    public static final String OBSCURED_STRING = "????";

    /** Number of spaces to use per indentation level. */
    private static final int DEFAULT_INDENTATION = 4;
    //endregion Constants

    /** Required - associates this object with its text. */
    public int messageId;
    
    /** The number of spaces this report should be indented. */
    private int indentation;

    /**
     * The number of newlines to add at the end of this report. Defaults to one.
     */
    public int newlines;

    /** The data values to fill in the report with. */
    private Vector<String> tagData;

    /** How to translate the tagData or not at all. */
    private String tagTranslate;

    /**
     * How this report is handled when double-blind play is in effect. See
     * constants above for more details.
     */
    public transient int type;

    /**
     * The entity this report concerns, if applicable. If this is left blank,
     * then the report will be considered <code>public</code>.
     */
    public transient int subject;
    
    /**
     * The player this report concerns, if applicable. This should be filled in
     * if this report is not public and still does not belong to a specific
     * visible entity
     */
    public transient int player;

    /**
     * This hash table will store the tagData Vector indexes that are supposed
     * to be obscured before sending to clients. This only applies when the
     * report type is "obscured".
     */
    private Map<Integer, Boolean> obscuredIndexes;

    /**
     * Vector to store the player names of those who received an obscured
     * version of this report. Used to reconstruct individual client's reports
     * from the master copy stored by the server.
     */
    private Vector<String> obscuredRecipients;

    /** Keep track of what data we have already substituted for tags. */
    private transient int tagCounter;

    /** bool for determining when code should be used to show image. */
    private transient boolean showImage;

    /** string to add to reports to show sprites **/
    private String imageCode;
    //endregion Variable Declarations

    //region Constructors
    /**
     * Default constructor, note that using this means the <code>messageId</code> field must be
     * explicitly set.
     */
    public Report() {
        this(MESSAGE_NONE);
    }

    public Report(final int id) {
        this(id, ReportType.HIDDEN);
    }

    public Report(final int id, final ReportType type) {
        this(id, Entity.NONE, type);
    }

    public Report(final int id, final int subject, final ReportType type) {
        this(id, subject, 1, type);
    }

    public Report(final int id, final int subject, final int newlines) {
        this(id, subject, newlines, ReportType.HIDDEN);
    }

    //region int... data
    public Report(final int id, final ReportType type, final int... data) {
        this(id, type, true, data);
    }

    public Report(final int id, final ReportType type, final boolean obscure, final int... data) {
        this(id, Entity.NONE, type, obscure, data);
    }

    public Report(final int id, final int subject, final ReportType type, final int... data) {
        this(id, subject, type, true, data);
    }

    public Report(final int id, final int subject, final ReportType type, final boolean obscure,
                  final int... data) {
        this(id, subject, 1, type, obscure, data);
    }

    public Report(final int id, final int subject, final int newlines, final ReportType type,
                  final int... data) {
        this(id, subject, newlines, type, true, data);
    }

    public Report(final int id, final int subject, final int newlines, final ReportType type,
                  final boolean obscure, final int... data) {
        this(id, subject, newlines, type);
        add(obscure, data);
    }
    //endregion int... data

    //region String... data
    public Report(final int id, final ReportType type, final String... data) {
        this(id, type, true, data);
    }

    public Report(final int id, final ReportType type, final boolean obscure, final String... data) {
        this(id, Entity.NONE, type, obscure, data);
    }

    public Report(final int id, final int subject, final ReportType type, final String... data) {
        this(id, subject, type, true, data);
    }

    public Report(final int id, final int subject, final ReportType type, final boolean obscure,
                  final String... data) {
        this(id, subject, 1, type, obscure, data);
    }

    public Report(final int id, final int subject, final int newlines, final ReportType type,
                  final String... data) {
        this(id, subject, newlines, type, true, data);
    }

    public Report(final int id, final int subject, final int newlines, final ReportType type,
                  final boolean obscure, final String... data) {
        this(id, subject, newlines, type);
        add(obscure, data);
    }
    //endregion String... data

    public Report(final int id, final int subject, final int newlines, final ReportType type) {
        this(id, subject, newlines, type.ordinal());
    }

    public Report(final int id, final int subject, final int newlines, final int type) {
        setMessageId(id);
        setIndentation(0);
        setNewlines(newlines);
        setTagData(new Vector<>());
        setTagTranslate(null);
        setType(type);
        setSubject(subject);
        setPlayer(IPlayer.PLAYER_NONE);
        setObscuredIndexes(new Hashtable<>());
        setObscuredRecipients(new Vector<>());
        setTagCounter(0);
        setShowImage(false);
        setImageCode("");
    }

    /**
     * Create a new report which is an exact copy of the given report.
     *
     * @param report the report to be copied
     */
    @SuppressWarnings(value = "unchecked")
    public Report(final Report report) {
        setMessageId(report.getMessageId());
        setIndentation(report.getIndentation());
        setNewlines(report.getNewlines());
        setTagData((Vector<String>) report.getTagData().clone());
        setTagTranslate(report.getTagTranslate());
        setType(report.getType());
        setSubject(report.getSubject());
        setObscuredIndexes((Map<Integer, Boolean>) ((Hashtable<Integer, Boolean>) report.getObscuredIndexes()).clone());
        setObscuredRecipients((Vector<String>) report.getObscuredRecipients().clone());
        setTagCounter(report.getTagCounter());
        setShowImage(report.isShowImage());
        setImageCode(report.getImageCode());
    }

    @Deprecated
    public Report(final int id, final int type) {
        this(id, Entity.NONE, 1, type);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(final int messageId) {
        this.messageId = messageId;
    }

    public int getIndentation() {
        return indentation;
    }

    public void setIndentation(final int indentation) {
        this.indentation = indentation;
    }

    public int getNewlines() {
        return newlines;
    }

    public void setNewlines(final int newlines) {
        this.newlines = newlines;
    }

    public Vector<String> getTagData() {
        return tagData;
    }

    public void setTagData(final Vector<String> tagData) {
        this.tagData = tagData;
    }

    public String getTagTranslate() {
        return tagTranslate;
    }

    public void setTagTranslate(final String tagTranslate) {
        this.tagTranslate = tagTranslate;
    }

    public int getType() {
        return type;
    }

    public ReportType getTypeEnum() {
        return ReportType.values()[getType()];
    }

    public void setType(final ReportType type) {
        setType(type.ordinal());
    }

    public void setType(final int type) {
        this.type = type;
    }


    public int getSubject() {
        return subject;
    }

    public void setSubject(final int subject) {
        this.subject = subject;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(final int player) {
        this.player = player;
    }

    public Map<Integer, Boolean> getObscuredIndexes() {
        return obscuredIndexes;
    }

    public void setObscuredIndexes(final Map<Integer, Boolean> obscuredIndexes) {
        this.obscuredIndexes = obscuredIndexes;
    }

    public Vector<String> getObscuredRecipients() {
        return obscuredRecipients;
    }

    public void setObscuredRecipients(final Vector<String> obscuredRecipients) {
        this.obscuredRecipients = obscuredRecipients;
    }

    public int getTagCounter() {
        return tagCounter;
    }

    public void setTagCounter(int tagCounter) {
        this.tagCounter = tagCounter;
    }

    public boolean isShowImage() {
        return showImage;
    }

    public void setShowImage(final boolean showImage){
        this.showImage = showImage;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
    //endregion Getters/Setters

    /**
     * Adds the given int(s) to the list of data as obscured
     * @param data the int(s) to be substituted
     */
    public void add(final int... data) {
        add(true, data);
    }

    /**
     * Add the given int(s) to the list of data that will be substituted for the <data> tags in the
     * report, and market it as double-blind sensitive if <tag>obscure</tag> is true.
     * The order in which items are added must match the order of the tags in the report text.
     *
     * @param obscure boolean indicating whether the data is double-blind sensitive
     * @param data the int to be substituted
     */
    public void add(final boolean obscure, final int... data) {
        for (final int dataPoint : data) {
            if (obscure) {
                obscuredIndexes.put(tagData.size(), true);
            }
            tagData.addElement(String.valueOf(dataPoint));
        }
    }

    /**
     * Adds the given String(s) to the list of data as obscured
     * @param data the String(s) to be substituted
     */
    public void add(final String... data) {
        add(true, data);
    }

    /**
     * Add the given String(s) to the list of data that will be substituted for the <data> tags in
     * the report, and market it as double-blind sensitive if <tag>obscure</tag> is true.
     * The order in which items are added must match the order of the tags in the report text.
     *
     * @param obscure boolean indicating whether the data is double-blind sensitive
     * @param data the String(s) to be substituted
     */
    public void add(final boolean obscure, final String... data) {
        for (final String dataPoint : data) {
            if (obscure) {
                obscuredIndexes.put(tagData.size(), true);
            }
            tagData.addElement(dataPoint);
        }
    }

    /**
     * Indicate which of two possible messages should be substituted for the
     * <code>&lt;msg:<i>n</i>,<i>m</i>&gt; tag.  An argument of
     * <code>true</code> would select message <i>n</i> while an
     * argument of <code>false</code> would select <i>m</i>.  In the
     * future, this capability may be expanded to support more than
     * two choices.
     *
     * @param choice boolean indicating which message to substitute
     */
    public void choose(boolean choice) {
        tagData.addElement(String.valueOf(choice));
    }

    /**
     * Shortcut method for adding entity name and owner data at the same time.
     * Assumes that the entity name should be obscured, but the owner should
     * not.
     *
     * @param entity the entity you wish to add
     */
    public void addDesc(Entity entity) {
        if (entity != null) {
            if (showImage || (indentation <= MMConstants.DEFAULT_SPACE_INDENTATION)) {
                imageCode = "<span id='" + entity.getId() + "'></span>";
            }
            add(true, "<font color='0xffffff'><a href=\"#entity:" + entity.getId()
                    + "\">" + entity.getShortName() + "</a></font>");
            add("<B><font color='" + entity.getOwner().getColour().getHexString(0x00F0F0F0) + "'>"
                    + entity.getOwner().getName() + "</font></B>");
        }
    }

    /**
     * Internal method. Not for typical use.
     * <p>
     * Tests wheter the data value at the given index has been marked as
     * obscured.
     *
     * @param index position of data value (indexes are chronological and start
     *            at zero)
     * @return true if the data value was marked obscured
     */
    public boolean isValueObscured(int index) {
        return obscuredIndexes.get(index) != null;
    }

    /**
     * Internal method. Not for typical use.
     * <p>
     * Remove the data value from the report. This operation is irreversible.
     *
     * @param index position of data value (indexes are chronological and start
     *            at zero
     */
    public void hideData(int index) {
        tagData.setElementAt(null, index);
    }

    /**
     * Indent the report.
     */
    public void indent() {
        indent(1);
    }

    /**
     * Indent the report <i>n</i> times.
     *
     * @param n the number of times to indent the report
     */
    public void indent(int n) {
        indentation += (n * Report.DEFAULT_INDENTATION);
    }

    /**
     * Internal method. Not for typical use.
     * <p>
     * Get the total number of data values associated with this report. Note
     * that this includes the <code>true/false</code> values added for
     * &lt;msg&gt; tags as well.
     *
     * @return the number of data values
     */
    public int dataCount() {
        return tagData.size();
    }

    private String getTag() {
        return getTag(tagCounter);
    }

    private String getTag(int index) {
        try {
            String value = tagData.elementAt(index);
            if (value == null) {
                return Report.OBSCURED_STRING;
            } else if (tagTranslate != null) {
                // Each common Resource Bundle is found below
                if (tagTranslate.equals("Messages")) {
                    return Messages.getString(value);
                }
            }
            return value;
        } catch (ArrayIndexOutOfBoundsException e) {
            MegaMek.getLogger().error("Array Index out of Bounds Exception (index: "
                    + index + ") for a report with ID " + messageId
                    + ".  Maybe Report::add wasn't called enough times for the amount of tags in the message?", e);
            return "[Reporting Error: see megameklog.txt for details]";
        }
    }

    /**
     * Get the report in its final form, with all the necessary substitutions made.
     *
     * @return a String with the final report
     */
    public String getText() {
        // The raw text of the message, with tags.
        String raw = ReportMessages.getString(String.valueOf(messageId));

        // This will be the finished product, with data substituted for tags.
        StringBuffer text = new StringBuffer();

        if (raw == null) {
            // Should we handle this better? Check alternate language files?
            MegaMek.getLogger().error("No message found for ID " + messageId);
            text.append("[Reporting Error for message ID ").append(messageId).append("]");
        } else {
            int i = 0;
            int mark = 0;
            while (i < raw.length()) {
                if (raw.charAt(i) == '<') {
                    // find end of tag
                    int endTagIdx = raw.indexOf('>', i);
                    if ((raw.indexOf('<', i + 1) != -1)
                            && (raw.indexOf('<', i + 1) < endTagIdx)) {
                        // hmm...this must be a literal '<' character
                        i++;
                        continue;
                    }
                    // copy the preceding characters into the buffer
                    text.append(raw, mark, i);
                    if (raw.substring(i + 1, endTagIdx).equals("data")) {
                        text.append(getTag());
                        tagCounter++;
                    } else if (raw.substring(i + 1, endTagIdx).equals("list")) {
                        for (int j = tagCounter; j < tagData.size(); j++) {
                            text.append(getTag(j)).append(", ");
                        }
                        text.setLength(text.length() - 2); // trim last comma
                    } else if (raw.substring(i + 1, endTagIdx).startsWith(
                            "msg:")) {
                        boolean selector = Boolean.parseBoolean(getTag());
                        if (selector) {
                            text.append(ReportMessages.getString(raw.substring(
                                    i + 5, raw.indexOf(',', i))));
                        } else {
                            text.append(ReportMessages.getString(raw.substring(
                                    raw.indexOf(',', i) + 1, endTagIdx)));
                        }
                        tagCounter++;
                    } else if (raw.substring(i + 1, endTagIdx)
                            .equals("newline")) {
                        text.append("\n");
                    } else {
                        // not a special tag, so treat as literal text
                        text.append(raw.substring(i, endTagIdx + 1));
                    }
                    mark = endTagIdx + 1;
                    i = endTagIdx;
                }
                i++;
            }
            //add the sprite code at the beginning of the line
            if (imageCode != null && !imageCode.isEmpty()) {
                if (text.toString().startsWith("\n")) {
                    text.insert(1, imageCode);
                }
                else {
                    text.insert(0, imageCode);
                }
            }
            text.append(raw.substring(mark));
            handleIndentation(text);
            text.append(MMConstants.DEFAULT_NEWLINE.repeat(getNewlines()));
        }
        tagCounter = 0;
        // debugReport
        if (getTypeEnum().isTesting()) {
            Report.mark(text);
        }
        return text.toString();
    }

    private void handleIndentation(StringBuffer sb) {
        if ((indentation == 0) || (sb.length() == 0)) {
            return;
        }
        int i = 0;
        while (sb.substring(i, i + 4).equals("\n")) {
            i += 4;
        }
        sb.insert(i, "&nbsp".repeat(getIndentation()));
    }

    /**
     * Adds a newline to the last report in the given Vector.
     *
     * @param v a Vector of Report objects
     */
    public static void addNewline(Vector<Report> v) {
        try {
            v.elementAt(v.size() - 1).newlines++;
        } catch (ArrayIndexOutOfBoundsException e) {
            MegaMek.getLogger().error("Report.addNewline failed, array index out of bounds", e);
        }
    }

    /**
     * Internal method. Not for typical use.
     * <p>
     * Adds the given player name to the report's list of players who received
     * an obscured version of this report from the server at some time in the
     * past.
     *
     * @param playerName the String containing the player's name
     */
    public void addObscuredRecipient(String playerName) {
        obscuredRecipients.addElement(playerName);
    }

    /**
     * Internal method. Not for typical use.
     * <p>
     * Tests whether the given player name is on the report's list of players
     * who received an obscured version of this report from the server at some
     * time in the past.
     *
     * @param playerName the String containing the player's name
     * @return true if the player was sent an obscured version of this report
     */
    public boolean isObscuredRecipient(String playerName) {
        for (int i = 0; i < obscuredRecipients.size(); i++) {
            String s = obscuredRecipients.elementAt(i);
            if (s.equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    private static void mark(final StringBuffer sb) {
        sb.insert(0, "<hidden>");
        int i = sb.length() - 1;
        while (sb.charAt(i) == '\n') {
            i--;
        }
        sb.insert(i + 1, "</hidden>");
    }

    public static void indentAll(final @Nullable Vector<Report> reports, final int indent) {
        if (reports == null) {
            return;
        }

        for (Report report : reports) {
            report.indent(indent);
        }
    }

    /**
     * @return a String of the form "Report(messageId=n)"
     */
    @Override
    public String toString() {
        return "Report(messageId=" + messageId + ")";
    }
}
