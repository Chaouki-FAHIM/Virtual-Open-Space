declare module 'react-autosuggest' {
    import { Component, ChangeEvent, FormEvent, FocusEvent, MouseEvent, ReactNode } from 'react';

    interface InputProps {
        value: string;
        onChange: (event: ChangeEvent<HTMLInputElement>, params: { newValue: string; method: string }) => void;
        onBlur?: (event: FocusEvent<HTMLInputElement>) => void;
        onFocus?: (event: FocusEvent<HTMLInputElement>) => void;
        type?: string;
        placeholder?: string;
        [key: string]: any;
    }

    interface SuggestionHighlightedProps {
        id: string;
        query: string;
        suggestion: any;
    }

    interface SuggestionSelectedEventData<T> {
        suggestion: T;
        suggestionValue: string;
        suggestionIndex: number;
        sectionIndex: number | null;
        method: 'click' | 'enter';
    }

    interface SuggestionsFetchRequestedParams {
        value: string;
        reason: string;
    }

    interface SuggestionsFetchRequested {
        (params: SuggestionsFetchRequestedParams): void;
    }

    interface SuggestionsClearRequested {
        (): void;
    }

    interface GetSuggestionValue<T> {
        (suggestion: T): string;
    }

    interface RenderSuggestion<T> {
        (suggestion: T, params: { query: string }): ReactNode;
    }

    interface RenderSectionTitle {
        (section: any): ReactNode;
    }

    interface GetSectionSuggestions {
        (section: any): any[];
    }

    interface OnSuggestionSelected<T> {
        (event: FormEvent<any> | MouseEvent<any>, data: SuggestionSelectedEventData<T>): void;
    }

    interface Theme {
        [key: string]: string;
    }

    interface MultiSectionProps<T> {
        multiSection: true;
        suggestions: any[];
        renderSectionTitle: RenderSectionTitle;
        getSectionSuggestions: GetSectionSuggestions;
        getSuggestionValue: GetSuggestionValue<T>;
        renderSuggestion: RenderSuggestion<T>;
        inputProps: InputProps;
        onSuggestionsFetchRequested: SuggestionsFetchRequested;
        onSuggestionsClearRequested: SuggestionsClearRequested;
        onSuggestionSelected?: OnSuggestionSelected<T>;
        theme?: Theme;
        id?: string;
    }

    interface SingleSectionProps<T> {
        multiSection?: false;
        suggestions: T[];
        getSuggestionValue: GetSuggestionValue<T>;
        renderSuggestion: RenderSuggestion<T>;
        inputProps: InputProps;
        onSuggestionsFetchRequested: SuggestionsFetchRequested;
        onSuggestionsClearRequested: SuggestionsClearRequested;
        onSuggestionSelected?: OnSuggestionSelected<T>;
        theme?: Theme;
        id?: string;
    }

    type AutosuggestProps<T> = MultiSectionProps<T> | SingleSectionProps<T>;

    class Autosuggest<T> extends Component<AutosuggestProps<T>> {}

    export default Autosuggest;
}
