export function formatDateToIso(rawString: string | null): string | null {
  if (!rawString) return null;
  const rawDate: Date = new Date(rawString);

  const yyyy = rawDate.getFullYear();
  const mm = (rawDate.getMonth() + 1).toString().padStart(2, '0');
  const dd = rawDate.getDate().toString().padStart(2, '0');

  return `${yyyy}-${mm}-${dd}`;
}

export function formatTimeToIso(rawString: Date | null): string | null {
  if (!rawString) return null;
  const rawDate: Date = new Date(rawString);
  const hh = rawDate.getHours().toString().padStart(2, '0');
  const mm = rawDate.getMinutes().toString().padStart(2, '0');
  const ss = rawDate.getSeconds().toString().padStart(2, '0');

  return `${hh}:${mm}:${ss}`;
}
