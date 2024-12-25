import moment from "moment";

export const formatDateRange = (dates) => {
  if (dates && dates.length === 2) {
    const [start, end] = dates;
    const formattedStart = start ? start.format("YYYY-MM-DDTHH:mm:ss") : null;
    const formattedEnd = end ? end.format("YYYY-MM-DDTHH:mm:ss") : null;
    return { formattedStart, formattedEnd };
  }
  return { formattedStart: null, formattedEnd: null };
};
